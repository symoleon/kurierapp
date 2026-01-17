import { z } from 'zod';

export const addressSchema = z.object({
    city: z.string(),
    street: z.string().optional(),
    building_no: z.string(),
    postal_code: z.string(),
    apartment_no: z.string().optional(),
});

export const shipmentSchema = z.object({
    id: z.uuid().optional(),
    recipientAddress: addressSchema,
    recipientPhone: z.string(),
    size: z.enum(["S", "M", "L", "XL"]),
});

export const userSchema = z.object({
    id: z.uuid().optional(),
    email: z.email(),
    phone: z.string(),
    address: addressSchema
})

export const userWithIdSchema = userSchema.extend({
    id: z.string(),
})