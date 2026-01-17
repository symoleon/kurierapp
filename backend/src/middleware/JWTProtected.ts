import type { Response, Request, NextFunction } from "express";
import { jwtVerify } from "jose";
import type { AuthRequest } from "../types.ts";

const secret = new TextEncoder().encode(process.env.JWT_SECRET);

async function JWTProtectedMiddleware(req: Request, res: Response, next: NextFunction) {
    const authHeader = req.headers.authorization;
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
        return res.sendStatus(401);
    }
    const token = authHeader.split(" ")[1];

    if (!token) {
        return res.sendStatus(403);
    }

    try {
        const { payload } = await jwtVerify<{ user: string }>(token, secret);
        (req as AuthRequest).userId = payload.user;
        next();
    } catch (err) {
        res.sendStatus(403);
    }
};

export default JWTProtectedMiddleware;