import { Component, OnInit } from '@angular/core';
import { HistorijaService } from '../_services/historija-management/historija.service';

@Component({
  selector: 'app-reviewer-articles',
  templateUrl: './reviewer-articles.component.html',
  styleUrls: ['./reviewer-articles.component.css']
})
export class ReviewerArticlesComponent implements OnInit {
  clanci: any[];
  /*clanci: any[] = [ {"nazivClanka":"Clanak 1", "autori":"Autor1", "kategorija":"Kategorija 1"}, 
                    {"nazivClanka":"Clanak 2", "autori":"Autor2", "kategorija":"Kategorija 7"},
                    {"nazivClanka":"Clanak 3", "autori":"Autor 3", "kategorija":"Kategorija 3"},
                    {"nazivClanka":"Clanak 4", "autori":"Autor 17", "kategorija":"Kategorija 9"}, 
                    {"nazivClanka":"Clanak 5", "autori":"Autor 5", "kategorija":"Kategorija 1"}] ; 
*/
  constructor(public historijaManagement: HistorijaService) { }

  ngOnInit() {
	  //this.refreshClanci();
  }

  async refreshClanci(){
    const data = await this.historijaManagement.AllClanak();
	for (var clanak of data) {
		const autoriData = await this.historijaManagement.autorByClanak(clanak.id);
		clanak.autori = autoriData;
	}
    
    /*if (data!=undefined && data._embedded!=undefined)
    {
      this.korisnici=data._embedded.userEntities;
      console.log(this.korisnici);
    }
    else this.korisnici=[]
  }*/
  this.clanci = data;
  console.log(this.clanci);
}

  prikazi() {
   this.refreshClanci();
  }

}
