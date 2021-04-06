import { Byte } from "@angular/compiler/src/util";

export interface User {
    ID: number;
    username: Byte;
    password: Byte;
    salt: Byte;
    email: Byte;
}
