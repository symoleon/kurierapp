import { z } from "zod";
import type { Request } from "express";
import {
    addressSchema,
    partialAddressSchema,
    shipmentSchema,
    userSchema,
    userWithIdSchema,
    partialUserSchema,
} from "./schema.ts";

export type Address = z.infer<typeof addressSchema>;
export type PartialAddress = z.infer<typeof partialAddressSchema>;
export type Shipment = z.infer<typeof shipmentSchema>;
export type User = z.infer<typeof userSchema>;
export type UserWithId = z.infer<typeof userWithIdSchema>;
export type PartialUser = z.infer<typeof partialUserSchema>;

export type DbUser = {
    id?: string;
    email?: string;
    phone?: string;
    password?: string;
    city?: string;
    postalCode?: string;
    street?: string;
    building_no?: string;
    apartment_no?: string;
};

export type Size = "S" | "M" | "L" | "XL";

export interface AuthRequest extends Request {
    userId: string;
}