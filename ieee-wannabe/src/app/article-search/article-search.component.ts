import { Component, OnInit } from '@angular/core';
import { ClanakService } from '../_services/clanak-management/clanak.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-search',
  templateUrl: './article-search.component.html',
  styleUrls: ['./article-search.component.css']
})
export class ArticleSearchComponent implements OnInit {

  filtriraniClanci: any[];
  listaAutora: any[];
  filterText: string = "";
  

  constructor(public clanakManagement: ClanakService, public router: Router) { }

  async refreshClanci(){
    if(this.filterText !== ""){
      const data = await this.clanakManagement.allClanakByNaziv(this.filterText);
      for (var clanak of data) {
        const autoriData = await this.clanakManagement.autorByClanak(clanak.id);
        clanak.autori = autoriData;
      }
      console.log(data);
      this.filtriraniClanci = data;
      console.log(this.filtriraniClanci);
    }
    else{
      const data = await this.clanakManagement.AllClanak();
      this.filtriraniClanci = data;
    }
}

  async refreshClanciPoKategoriji(){
    const data = await this.clanakManagement.allClanakByKategorija(this.filterText);
	  for (var clanak of data) {
      const autoriData = await this.clanakManagement.autorByClanak(clanak.id);
      clanak.autori = autoriData;
	  }
    console.log(data);
    this.filtriraniClanci = data;
    console.log(this.filtriraniClanci);
}

  ngOnInit() {
    console.log("here");
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

  openArticle(id:number){
    console.log(id);
    this.router.navigate(['/author/' + id.toString()]);
  }

}
