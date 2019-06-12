import { Korisnik } from './korisnik';
import { Clanak } from './clanak';

export class Autori {
    id? : number;
    idKorisnik: Korisnik;
    idClanak: Clanak;
    constructor(idKorisnik, idClanak){
        this.idKorisnik = idKorisnik;
        this.idClanak = idClanak;
    }
}