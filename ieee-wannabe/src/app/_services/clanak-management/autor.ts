import { Korisnik } from './korisnik';
import { Clanak } from './clanak';

export class Autor {
    id? : number;
    idKorisnik: Korisnik;
    idClanakA: Clanak;
}