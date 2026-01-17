import { z } from 'zod';

export const addressSchema = z.object({
    city: z.string(),
    street: z.string().optional(),
    building_no: z.string(),
    postal_code: z.string(),
    apartment_no: z.string().optional(),
});

export const partialAddressSchema = addressSchema.partial();

export const shipmentSchema = z.object({
    id: z.uuid(),
    senderId: z.uuid(),
    recipientAddress: addressSchema,
    recipientPhone: z.string(),
    recipientId: z.uuid().optional(),
    size: z.enum([ "S", "M", "L", "XL" ]),
});

export const partialShipmentSchema = shipmentSchema.partial();

export const createShipmentSchema = shipmentSchema.omit({
    id: true,
    senderId: true,
    recipientId: true,
});

export const userSchema = z.object({
    id: z.uuid(),
    email: z.email(),
    phone: z.string(),
    address: addressSchema,
});

export const partialUserSchema = userSchema.partial();

export const createUserSchema = userSchema.omit({
    id: true,
}).extend({
    password: z.string(),
});