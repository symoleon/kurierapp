import { describe, test, expect } from "bun:test";
import {createShipmentSchema} from "../src/schema.ts";

describe("/api/shipment", () => {
   test("Poprawność schematu", () => {
      const data = {
          "recipientAddress": {
              "city": "Warszawa",
              "postal_code": "00-950",
              "building_no": "15",
              "street": "Aleje Jerozolimskie"
          },
          "recipientPhone": "500-100-200",
          "size": "M"
      };
      const result = createShipmentSchema.safeParse(data);
      expect(result.success).toEqual(true);
   });
    test("Walidacja błędnych danych", () => {
        const data = {
            "recipientAddress": {
                "postal_code": "00-950",
                "building_no": "15",
                "street": "Aleje Jerozolimskie"
            },
            "recipientPhone": "500-100-200",
            "size": "big"
        };
        const result = createShipmentSchema.safeParse(data);
        expect(result.success).toEqual(false);
    });
});