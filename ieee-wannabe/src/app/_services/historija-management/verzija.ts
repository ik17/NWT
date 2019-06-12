import { Korisnik } from './korisnik';
import { Clanak } from './clanak';

export class Verzija {
    id? : number;
    verzijaClanka: number;
    link: string;
    review: string;
    idClanak: Clanak;
    
    
    
    constructor(idClanak, verzijaClanka, linkNaClanak, reviewVerzije){
        this.idClanak = idClanak;
        this.verzijaClanka = verzijaClanka;
        this.link = linkNaClanak;
        this.review = reviewVerzije;
    }
}