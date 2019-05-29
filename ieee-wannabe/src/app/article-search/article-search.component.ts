import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-article-search',
  templateUrl: './article-search.component.html',
  styleUrls: ['./article-search.component.css']
})
export class ArticleSearchComponent implements OnInit {

  clanci: any[] = [ {"nazivClanka":"Clanak 1", "autori":"Autor 1", "kategorija":"Kategorija 1"}, 
                    {"nazivClanka":"Clanak 2", "autori":"Autor 2", "kategorija":"Kategorija 7"},
                    {"nazivClanka":"Clanak 3", "autori":"Autor 3", "kategorija":"Kategorija 3"},
                    {"nazivClanka":"Clanak 4", "autori":"Autor 17", "kategorija":"Kategorija 9"} ]; 
  filtriraniClanci: any[];
  filterText: string = "";

  constructor() { }

  ngOnInit() {
    this.filtriraniClanci = this.clanci;
  }

  filtrirajClanke(): void {
    this.filtriraniClanci= (this.filterText ? this.performFilter(this.filterText) : this.clanci);
  }

  performFilter(filterBy: string): any[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.clanci.filter((clanak: any) => 
          clanak.nazivClanka.toLocaleLowerCase().indexOf(filterBy) !== -1 || 
          clanak.autori.toLocaleLowerCase().indexOf(filterBy) !== -1 || 
          clanak.kategorija.toLocaleLowerCase().indexOf(filterBy) !== -1);
}

}
