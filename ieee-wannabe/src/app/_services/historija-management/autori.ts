import { Korisnik } from './korisnik';
import { Clanak } from './clanak';

export class Autori {
    id? : number;
    idKorisnik: Korisnik;
    idClanakA: Clanak;
}