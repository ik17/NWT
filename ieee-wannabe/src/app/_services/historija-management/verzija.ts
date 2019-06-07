import { Korisnik } from './korisnik';
import { Clanak } from './clanak';

export class Verzija {
    id? : number;
    idClanak: Clanak;
    verzijaClanka: number;
    linkNaClanak: string;
    reviewVerzije: string;
}