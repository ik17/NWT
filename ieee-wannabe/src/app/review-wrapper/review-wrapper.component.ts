import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Clanak } from '../_services/clanak-management/clanak';

@Component({
  selector: 'app-review-wrapper',
  templateUrl: './review-wrapper.component.html',
  styleUrls: ['./review-wrapper.component.css']
})
export class ReviewWrapperComponent implements OnInit {
  clanak: Clanak;
  nazivClanka: string = "Članak 1";
  autori: string[] = [ "Autor 1", "Autor 2", "Autor 3" ];
  kategorija: string = "Kategorija 1";
  urlZaPreuzimanje: string = "https://www.google.com/";
  komentar: string = "";
  komentari: any[] = [ { "korisnik":"user1", tekst:"ovo je moj komentar na ovo"},
                       { "korisnik":"user2", tekst:"a ovo je moj komentar na ovo"},            
                       { "korisnik":"user123", tekst:"slazem se sa user1"} ];

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
  }

  showAuthors(): string {
    return this.autori.join(", ");
  }

}
