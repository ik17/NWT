import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reviewer-articles',
  templateUrl: './reviewer-articles.component.html',
  styleUrls: ['./reviewer-articles.component.css']
})
export class ReviewerArticlesComponent implements OnInit {
  clanci: any[] = [ {"nazivClanka":"Clanak 1", "autori":"Autor1", "kategorija":"Kategorija 1"}, 
                    {"nazivClanka":"Clanak 2", "autori":"Autor2", "kategorija":"Kategorija 7"},
                    {"nazivClanka":"Clanak 3", "autori":"Autor 3", "kategorija":"Kategorija 3"},
                    {"nazivClanka":"Clanak 4", "autori":"Autor 17", "kategorija":"Kategorija 9"}, 
                    {"nazivClanka":"Clanak 5", "autori":"Autor 5", "kategorija":"Kategorija 1"}] ; 

  constructor() { }

  ngOnInit() {
  }

}
