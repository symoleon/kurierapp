import { Router } from 'express';
import { getShipment } from "../utils/shipments.ts";
import type { AuthRequest } from "../types.ts";

const router = Router();
router.get('/:id', async (req, res) => {
    const authReq = req as unknown as AuthRequest;
    const shipment = await getShipment(req.params.id);
    if (!shipment) return res.sendStatus(403);
    if (shipment.recipientId != authReq.userId || shipment.senderId != authReq.userId) return res.sendStatus(403);
    return res.status(200).send(shipment);
});

export default router;
