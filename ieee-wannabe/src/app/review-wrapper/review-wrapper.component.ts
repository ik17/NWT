import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Clanak } from '../_services/clanak-management/clanak';
import { ClanakService } from '../_services/clanak-management/clanak.service';

@Component({
  selector: 'app-review-wrapper',
  templateUrl: './review-wrapper.component.html',
  styleUrls: ['./review-wrapper.component.css']
})
export class ReviewWrapperComponent implements OnInit {
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

  constructor(public clanakManagement: ClanakService, private route: ActivatedRoute) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
	this.idClanka = id;
	this.refreshClanak();
  }
  
  async refreshClanak(){
    const data = await this.clanakManagement.oneClanak(this.idClanka);
	this.nazivClanka = data.naziv;
	this.kategorija = data.kategorija.naziv;
	//this.urlZaPreuzimanje = data.linkNaClanak;
	
	//this.komentar = data.reviewVerzije;
	
	const autoriData = await this.clanakManagement.autorByClanak(data.id);
	this.autori = autoriData;
	
	// komentari
	const komentariData = await this.clanakManagement.komentarByClanak(data.id);
	this.komentari = komentariData;
	
    
   
}

  showAuthors(): string {
    return this.autori.join(", ");
  }

}
