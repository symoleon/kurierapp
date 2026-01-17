import { z } from "zod";
import {
    addressSchema,
    shipmentSchema,
    userSchema,
} from "./schema.ts";

export type Address = z.infer<typeof addressSchema>;
export type Shipment = z.infer<typeof shipmentSchema>;
export type User = z.infer<typeof userSchema>;

export type size = "S" | "M" | "L" | "XL";