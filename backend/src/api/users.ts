import { Router } from "express";
import type { AuthRequest, PartialUser } from "../types.ts";
import { getUserById, updateUser } from "../utils/users.ts";
import { partialUserSchema } from "../schema.ts";

const router = Router();

router.get('/@current', async (req, res) => {
    const authReq = req as AuthRequest;
    const user = await getUserById(authReq.userId);
    if (!user) {
        return res.sendStatus(403);
    }
    return res.status(200).send(user);
});

router.patch('/@current', async (req, res) => {
    const authReq = req as AuthRequest;
    const result = partialUserSchema.safeParse(authReq.body);
    if (!result.success) return res.sendStatus(400);
    const data = result.data;
    await updateUser(authReq.userId, data);
    const updatedUser = await getUserById(authReq.userId);
    return res.status(200).send(updatedUser);
});

export default router;