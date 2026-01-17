import { z } from "zod";
import {
    addressSchema,
    shipmentSchema,
    userSchema,
    userWithIdSchema,
} from "./schema.ts";

export type Address = z.infer<typeof addressSchema>;
export type Shipment = z.infer<typeof shipmentSchema>;
export type User = z.infer<typeof userSchema>;
export type UserWithId = z.infer<typeof userWithIdSchema>;

export type Size = "S" | "M" | "L" | "XL";