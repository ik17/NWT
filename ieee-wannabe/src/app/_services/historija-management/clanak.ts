import { Korisnik } from './korisnik';
import { Kategorija } from './kategorija';

export class Clanak {
    id? : number;
    naziv : string;
    idKategorije : Kategorija;
    clanakOdobren : number;
    odobrioClanak : Korisnik;
}