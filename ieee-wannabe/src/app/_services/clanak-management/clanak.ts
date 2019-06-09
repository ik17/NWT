import { Korisnik } from './korisnik';
import { Kategorija } from './kategorija';

export class Clanak {
    id? : number;
    naziv : string;
    kategorija : Kategorija;
    clanakOdobren : number;
    odobrioClanak : Korisnik;
}