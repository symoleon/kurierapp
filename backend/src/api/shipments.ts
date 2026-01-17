import { Router } from 'express';
import { addShipment, getShipment } from "../utils/shipments.ts";
import type { AuthRequest } from "../types.ts";
import { createShipmentSchema } from "../schema.ts";

const router = Router();
router.get('/:id', async (req, res) => {
    const authReq = req as unknown as AuthRequest;
    const shipment = await getShipment(req.params.id);
    if (!shipment) return res.sendStatus(403);
    if (shipment.recipientId != authReq.userId && shipment.senderId != authReq.userId) return res.sendStatus(403);
    return res.status(200).send(shipment);
});

router.post('/', async (req, res) => {
    const authReq = req as AuthRequest;
    const result = createShipmentSchema.safeParse(authReq.body);
    if (!result.success) return res.sendStatus(400);
    const shipment = result.data;
    const newShipment = await addShipment(authReq.userId, shipment);
    return res.status(200).send(newShipment);
});

export default router;
