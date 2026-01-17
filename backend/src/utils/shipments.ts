import { sql } from "bun";
import type { Shipment, Address, Size, DbShipment } from "../types.ts";
import { getUserByPhone } from "./users.ts";

function mapToShipment(dbShipment: DbShipment): Shipment {
    return {
        id: dbShipment.id,
        senderId: dbShipment.sender_id,
        size: dbShipment.size,
        recipientPhone: dbShipment.recipient_phone,
        recipientId: dbShipment.recipient_id,
        recipientAddress: {
            city: dbShipment.city,
            postal_code: dbShipment.postal_code,
            street: dbShipment.street,
            building_no: dbShipment.building_no,
            apartment_no: dbShipment.apartment_no,
        },
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
    const result = await sql`SELECT * FROM shipments WHERE id = ${id}`;
    if (result.length != 1) return null;
    return mapToShipment(result[0]);
}

export async function addShipment(userId: string, size: Size, recipientAddress: Address, recipientPhone: string) {
    const recipient = await getUserByPhone(recipientPhone);
    await sql`INSERT INTO shipments(sender_id, recipient_id, recipient_phone, size, city, postal_code,
                                    street, building_no, apartment_no)
              VALUES (${userId}, ${recipient?.id}, ${recipientPhone}, ${size}, ${recipientAddress.city},
                      ${recipientAddress.postal_code}, ${recipientAddress.street},
                      ${recipientAddress.building_no}, ${recipientAddress.apartment_no})`;
}