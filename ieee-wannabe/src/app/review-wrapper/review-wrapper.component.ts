import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Clanak } from '../_services/clanak-management/clanak';
import { ClanakService } from '../_services/clanak-management/clanak.service';
import { Komentar } from '../_services/clanak-management/komentar';
import { Korisnik } from '../_services/clanak-management/korisnik';
import * as jwt_decode from "jwt-decode";
@Component({
  selector: 'app-review-wrapper',
  templateUrl: './review-wrapper.component.html',
  styleUrls: ['./review-wrapper.component.css']
})
export class ReviewWrapperComponent implements OnInit {
	Komentar1 : any;
	idClanka: number = 1;
  clanak: Clanak;
  nazivClanka: string = "Članak 1";
  autori: any[] = [ "Autor 1", "Autor 2", "Autor 3" ];
  kategorija: string = "Kategorija 1";
  urlZaPreuzimanje: string = "https://www.google.com/";
  komentar: string = "";
  komentari: any[] = [ { "korisnik":"user1", tekst:"ovo je moj komentar na ovo"},
                       { "korisnik":"user2", tekst:"a ovo je moj komentar na ovo"},            
                       { "korisnik":"user123", tekst:"slazem se sa user1"} ];

  constructor(public clanakManagement: ClanakService, 
    private route: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
	  this.idClanka = id;
	  this.refreshClanak();
  }
  posaljiKomentar() {
	  this.sendClanak();
  }
  async sendClanak() {
	  console.log(this.komentar);
	  const dataClanak = await this.clanakManagement.oneClanak(this.idClanka);
	  const dataKorisnik = await this.clanakManagement.oneUser(jwt_decode(localStorage.getItem("id_token")).id);
	
	  this.Komentar1 = new Komentar(this.komentar, dataClanak, dataKorisnik );
	  const odg = await this.clanakManagement.createKomentar(this.Komentar1);
	  console.log(odg);
	  const komentariData = await this.clanakManagement.komentarByClanak(this.idClanka);
	  this.komentari = komentariData;
  }
  async refreshClanak(){
    const data = await this.clanakManagement.oneClanak(this.idClanka);
	  this.nazivClanka = data.naziv;
	  this.kategorija = data.kategorija.naziv;
	  this.urlZaPreuzimanje = data.linkNaClanak;
	
	//this.komentar = data.reviewVerzije;
	
  const autoriData = await this.clanakManagement.autorByClanak(data.id);
  console.log(autoriData);
	this.autori = autoriData;
	
	// komentari
	const komentariData = await this.clanakManagement.komentarByClanak(data.id);
	this.komentari = komentariData;
   
}

  showAuthors(): string {
    return this.autori.join(", ");
  }
  openUser(id:number){
    console.log(id);
    this.router.navigate(['/usera/' + id.toString()]);
  }
}
