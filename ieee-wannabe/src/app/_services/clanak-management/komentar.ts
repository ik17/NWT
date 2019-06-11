import { Clanak } from './clanak';
import { Korisnik } from './korisnik';

export class Komentar {
    id? : number;
    textKomentara: string;
    clanak: Clanak;
    korisnik: Korisnik;
	
	constructor(textKomentara, clanak, korisnik ) {
        this.textKomentara = textKomentara;
        this.clanak = clanak;
        this.korisnik = korisnik;
    
    }
	
}