import { sql } from "bun";
import type {
    Shipment,
    DbShipment,
    CreateShipment,
    PartialShipment,
    DbPartialShipment,
} from "../types.ts";
import { getUserByPhone } from "./users.ts";

function mapToShipment(dbShipment: DbShipment): Shipment {
    return {
        id: dbShipment.id,
        senderId: dbShipment.sender_id,
        size: dbShipment.size,
        name: dbShipment.name,
        recipientPhone: dbShipment.recipient_phone,
        recipientId: dbShipment.recipient_id,
        recipientAddress: {
            city: dbShipment.city,
            postal_code: dbShipment.postal_code,
            street: dbShipment.street,
            building_no: dbShipment.building_no,
            apartment_no: dbShipment.apartment_no,
        },
        state: dbShipment.state,
    };
}

function shipmentToDbObject(shipment: Shipment | PartialShipment | CreateShipment): DbPartialShipment {
    return {
        id: "id" in shipment ? shipment.id : undefined,
        sender_id: "senderId" in shipment ? shipment.senderId : undefined,
        size: shipment.size,
        name: shipment.name,
        recipient_id: "recipientId" in shipment ? shipment.recipientId : undefined,
        recipient_phone: shipment.recipientPhone,
        city: shipment.recipientAddress?.city,
        postal_code: shipment.recipientAddress?.postal_code,
        street: shipment.recipientAddress?.street,
        building_no: shipment.recipientAddress?.building_no,
        apartment_no: shipment.recipientAddress?.apartment_no,
        state: "state" in shipment ? shipment.state : undefined,
    };
}

export async function getShipments(userId: string, type: "SENDER" | "RECIPIENT"): Promise<Array<Shipment>> {
    if (type === "SENDER") {
        const result = await sql`SELECT *
                                 FROM shipments
                                 WHERE sender_id = ${userId}`;
        return result.map(mapToShipment);
    }
    if (type === "RECIPIENT") {
        const result = await sql`SELECT *
                                 FROM shipments
                                 WHERE recipient_id = ${userId}`;
        return result.map(mapToShipment);
    }
    return [];
}

export async function getShipment(id: string): Promise<Shipment | null> {
    const result = await sql`SELECT *
                             FROM shipments
                             WHERE id = ${id}`;
    if (result.length != 1) return null;
    return mapToShipment(result[0]);
}

export async function addShipment(userId: string, shipment: CreateShipment): Promise<Shipment> {
    const recipient = await getUserByPhone(shipment.recipientPhone);
    const newShipment = {
        ...shipment,
        recipientId: recipient?.id,
        senderId: userId,
    } satisfies PartialShipment;
    const { id, ...dbShipment } = shipmentToDbObject(newShipment);
    dbShipment.state = "paid";
    const [ addedShipment ] = await sql`INSERT INTO shipments ${sql(dbShipment)} RETURNING *`;
    return mapToShipment(addedShipment);
}