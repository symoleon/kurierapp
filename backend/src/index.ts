import express from 'express';
import z from 'zod';
import { shipmentSchema } from "./schema";
import authRouter from "./api/auth.ts";

const formatErrors = (error: z.ZodError): string[] => {
    return error.issues.map((issue) => {
        return `${issue.path.join('.')}: ${issue.message}`;
    });
};

const app = express();
const port = 3000;

app.use(express.json());

app.get('/', (req, res) => {
    res.send('Hello World!');
});

app.use("/api", authRouter);

app.post('/api/shipments', (req, res) => {
   const result = shipmentSchema.safeParse(req.body);
   if (!result.success) {
       res.status(400).json(formatErrors(result.error));
       return;
   }
   res.json(result.data);
});

app.listen(port, () => {
   console.log(`Server started on port ${port}`);
});