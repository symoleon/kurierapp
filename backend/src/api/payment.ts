import { Router } from "express";
import Stripe from "stripe";

const router = Router();

router.post("/payment", async (req, res) => {
    const stripe = new Stripe(process.env.STRIPE_SECRET!);
    const paymentIntent = await stripe.paymentIntents.create({
        amount: 2000,
        currency: 'pln',
        automatic_payment_methods: {
            enabled: true,
        }
    });
    res.json({
        paymentIntent: paymentIntent.client_secret,
    });
})

export default router;