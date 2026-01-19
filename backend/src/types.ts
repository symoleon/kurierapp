import { z } from "zod";
import type { Request } from "express";
import {
    addressSchema,
    partialAddressSchema,
    shipmentSchema,
    partialShipmentSchema,
    createShipmentSchema,
    userSchema,
    partialUserSchema,
    createUserSchema,
} from "./schema.ts";

export type Address = z.infer<typeof addressSchema>;
export type PartialAddress = z.infer<typeof partialAddressSchema>;
export type Shipment = z.infer<typeof shipmentSchema>;
export type PartialShipment = z.infer<typeof partialShipmentSchema>;
export type CreateShipment = z.infer<typeof createShipmentSchema>;
export type User = z.infer<typeof userSchema>;
export type CreateUser = z.infer<typeof createUserSchema>;
export type PartialUser = z.infer<typeof partialUserSchema>;

export type DbUser = {
    id: string;
    name: string;
    email: string;
    phone: string;
    password: string;
    city: string;
    postal_code: string;
    street?: string;
    building_no: string;
    apartment_no?: string;
};

export type DbPartialUser = Partial<DbUser>;

export type DbShipment = {
    id: string;
    sender_id: string;
    recipient_id?: string;
    recipient_phone: string;
    size: Size;
    name: string;
    city: string;
    postal_code: string;
    street?: string;
    building_no: string;
    apartment_no?: string;
    state: shipmentState;
};

export type DbPartialShipment = Partial<DbShipment>;

export type Size = "S" | "M" | "L" | "XL";
export type shipmentState = "created" | "paid" | "sent" | "delivered";

export interface AuthRequest extends Request {
    userId: string;
}