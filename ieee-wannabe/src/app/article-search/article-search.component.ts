import { Component, OnInit } from '@angular/core';
import { ClanakService } from '../_services/clanak-management/clanak.service';

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
  listaAutora: any[];
  filterText: string = "";
  

  constructor(public clanakManagement: ClanakService) { }

  async refreshClanci(){
    const data = await this.clanakManagement.allClanakByNaziv(this.filterText);
	for (var clanak of data) {
		const autoriData = await this.clanakManagement.autorByClanak(clanak.id);
		clanak.autori = autoriData;
	}
    console.log(data);
    /*if (data!=undefined && data._embedded!=undefined)
    {
      this.korisnici=data._embedded.userEntities;
      console.log(this.korisnici);
    }
    else this.korisnici=[]
  }*/
  this.filtriraniClanci = data;
}
  async refreshClanciPoKategoriji(){
    const data = await this.clanakManagement.allClanakByKategorija(this.filterText);
	for (var clanak of data) {
		const autoriData = await this.clanakManagement.autorByClanak(clanak.id);
		clanak.autori = autoriData;
	}
    console.log(data);
    /*if (data!=undefined && data._embedded!=undefined)
    {
      this.korisnici=data._embedded.userEntities;
      console.log(this.korisnici);
    }
    else this.korisnici=[]
  }*/
  this.filtriraniClanci = data;
}

  ngOnInit() {
    //this.filtriraniClanci = this.clanci;
    this.refreshClanci();
  }

  filtrirajClanke(): void {
    this.refreshClanci();
    //this.filtriraniClanci= (this.filterText ? this.performFilter(this.filterText) : this.clanci);
  }
filtrirajClankePoKategoriji(): void {
    this.refreshClanciPoKategoriji();
    //this.filtriraniClanci= (this.filterText ? this.performFilter(this.filterText) : this.clanci);
  }
  performFilter(filterBy: string): any[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.clanci.filter((clanak: any) => 
          clanak.nazivClanka.toLocaleLowerCase().indexOf(filterBy) !== -1 || 
          clanak.autori.toLocaleLowerCase().indexOf(filterBy) !== -1 || 
          clanak.kategorija.toLocaleLowerCase().indexOf(filterBy) !== -1);
}

}
