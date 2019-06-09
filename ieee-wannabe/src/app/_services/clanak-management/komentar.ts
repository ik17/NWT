import { Clanak } from './clanak';
import { Korisnik } from './korisnik';

export class Komentar {
    id? : number;
    textKomentara: string;
    clanak: Clanak;
    korisnik: Korisnik;
}