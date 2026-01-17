import { Router } from 'express';
import { sql } from "bun";
import { SignJWT } from "jose";

const router = Router();

router.post("/login", async (req, res) => {
    const {email, password} = req.body;
    const result = await sql`SELECT id, password
                             FROM users
                             WHERE email = ${email}`;
    if (result.length < 1) return res.status(401);
    const isCorrect = await Bun.password.verify(password, result[0].password);
    if (!isCorrect) return res.status(401);
    const secret = new TextEncoder().encode(process.env.JWT_SECRET);
    const jwt = await new SignJWT({user: result[0].id})
        .setProtectedHeader({alg: "HS256"})
        .sign(secret);
    return res.status(200).setHeader("Content-Type", "text/plain").send(jwt);
});

export default router;