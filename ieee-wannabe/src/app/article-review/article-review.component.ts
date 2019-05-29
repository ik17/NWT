import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-article-review',
  templateUrl: './article-review.component.html',
  styleUrls: ['./article-review.component.css']
})
export class ArticleReviewComponent implements OnInit {

  nazivClanka: string = "Članak 1";
  autori: string[] = [ "Autor 1", "Autor 2", "Autor 3" ];
  kategorija: string = "Kategorija 1";
  urlZaPreuzimanje: string = "https://www.google.com/";
  komentar: string = "";

  constructor() { }

  ngOnInit() {
  }

  showAuthors(): string {
    return this.autori.join(", ");
  }

  onClickPrihvati(): void {
    alert("Pritisnut je button Prihvati");
  }

  onClickOdbij(): void {
    alert("Pritisnut je button Odbij");
  }

}
