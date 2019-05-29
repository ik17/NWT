import { Podaci } from './podaci';
import { Uloga } from './uloga';

export class User {
    id? : number;
    username : string;
    password : string;
    korisnikPodaci : Podaci;
    ulogaKorisnik : Uloga;
}