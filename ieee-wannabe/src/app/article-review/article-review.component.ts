import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Clanak } from '../_services/historija-management/clanak';

@Component({
  selector: 'app-article-review',
  templateUrl: './article-review.component.html',
  styleUrls: ['./article-review.component.css']
})
export class ArticleReviewComponent implements OnInit {
  clanak: Clanak;
  nazivClanka: string = "Članak 1";
  autori: string[] = [ "Autor 1", "Autor 2", "Autor 3" ];
  kategorija: string = "Kategorija 1";
  urlZaPreuzimanje: string = "https://www.google.com/";
  komentar: string = "";

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
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
