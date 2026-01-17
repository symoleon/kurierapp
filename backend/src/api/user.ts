import { Router } from "express";
import type { AuthRequest } from "../types.ts";
import { getUserById } from "../utils/users.ts";

const router = Router();

router.get('/@current', async (req, res) => {
    const authReq = req as AuthRequest;
    const user = await getUserById(authReq.userId);
    console.log(user);
    if (!user) {
        return res.sendStatus(403);
    }
    return res.status(200).send(user);
})

export default router;