import { sql } from "bun";
import type { DbPartialUser, PartialUser, User, UserWithId } from "../types.ts";

function userToDbObject(user: PartialUser | User | UserWithId): DbPartialUser {
    return {
        id: "id" in user ? user.id : undefined,
        email: user.email,
        phone: user.phone,
        city: user.address?.city,
        postalCode: user.address?.postal_code,
        street: user.address?.street,
        building_no: user.address?.building_no,
        apartment_no: user.address?.apartment_no,
    };
}

export async function getUserById(id: string): Promise<UserWithId | null> {
    const [ user ] = await sql`SELECT *
                             FROM users
                             WHERE id = ${id}`;
    if (!user) return null;
    return {
        id: user.id,
        email: user.email,
        phone: user.phone,
        address: {
            city: user.city,
            street: user.street,
            postal_code: user.postal_code,
            building_no: user.building_no,
            apartment_no: user.apartment_no,
        }
    };
}

export async function getUserByPhone(phon: string): Promise<UserWithId | null> {
    const [ user ] = await sql`SELECT * FROM users WHERE phone = ${phon}`;
    if (!user) return null;
    return {
        id: user.id,
        email: user.email,
        phone: user.phone,
        address: {
            city: user.city,
            street: user.street,
            postal_code: user.postal_code,
            building_no: user.building_no,
            apartment_no: user.apartment_no,
        }
    };
}

export async function addUser(user: User, password: string): Promise<UserWithId> {
    const hash = await Bun.password.hash(password);
    const [ newUser ] = await sql`INSERT INTO users(email, password, phone, city, postal_code, street, building_no,
                                              apartment_no)
                            VALUES (${user.email},
                                    ${hash},
                                    ${user.phone},
                                    ${user.address.city},
                                    ${user.address.postal_code},
                                    ${user.address.street},
                                    ${user.address.building_no},
                                    ${user.address.apartment_no})`;
    return newUser;
}

export async function updateUser(userId: string, partialUser: PartialUser) {
    const { id, ...user } = userToDbObject(partialUser);
    await sql`UPDATE users SET ${sql(user)} WHERE id = ${userId}`;
}