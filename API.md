# Dokumentacja API

## Endpointy
| Metoda HTTP | Endpoint                     | Opis                        | Wymagany JWT? | Dane wejściowe (Body/Params) |
|-------------|------------------------------|-----------------------------|:-------------:|:----------------------------:|
| POST        | /api/login                   | Logowanie i pobranie tokena |      Nie      |     { login, password }      |
| GET         | /api/user/@current           | Pobranie danych profilu     |      Tak      |              -               |
| PATCH       | /api/user/@current           | Częściowa edycja profilu    |      Tak      |      { email?, phone? }      |
| GET         | /api/user/@current/shipments | Lista paczek użytkownika    |      Tak      |              -               |
| GET         | /api/shipments/:id           | Szczegóły konkretnej paczki |      Tak      |       Parametr URL :id       |
| POST        | /api/shipments               | Nadanie nowej paczki        |      Tak      |    [Shipment](#Shipment)     |

## Typy danych
### Shipment

| Pole             | Typ                       |
|------------------|---------------------------|
| recipientAddress | [Address](#Address)       |
| recipientPhone   | string                    |
| size             | "S" \| "M" \| "L" \| "XL" |

### Address

| Pole         | Typ     |
|--------------|---------|
| city         | string  |
| postal_code  | string  |
| building_no  | string  |
| street       | string? |
| apartment_no | string? |

