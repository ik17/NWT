import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Clanak } from '../_services/historija-management/clanak';
import { HistorijaService } from '../_services/historija-management/historija.service';

@Component({
  selector: 'app-article-review',
  templateUrl: './article-review.component.html',
  styleUrls: ['./article-review.component.css']
})
export class ArticleReviewComponent implements OnInit {
  clanak: Clanak;
  nazivClanka: string = "Članak 1";
  autori: any[] = [ "Autor 1", "Autor 2", "Autor 3" ];
  kategorija: string = "Kategorija 1";
  verzijaClanka: string = "1";
  urlZaPreuzimanje: string = "https://www.google.com/";
  komentar: string = "";
  idVerzije = -1;

  constructor(public historijaManagement: HistorijaService, 
    private route: ActivatedRoute, 
    public router: Router) { }
  

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
	this.idVerzije = id;
    console.log(id);
	this.refreshClanak();
	
  }
  async refreshClanak(){
    const data = await this.historijaManagement.oneVerzija(this.idVerzije);
	this.nazivClanka = data.idClanak.naziv;
	this.kategorija = data.idClanak.idKategorije.naziv;
	this.urlZaPreuzimanje = data.linkNaClanak;
	this.verzijaClanka = data.verzijaClanka;
	this.komentar = data.reviewVerzije;
	
	const autoriData = await this.historijaManagement.autorByClanak(data.idClanak.id);
	this.autori = autoriData;
	
    
    /*if (data!=undefined && data._embedded!=undefined)
    {
      this.korisnici=data._embedded.userEntities;
      console.log(this.korisnici);
    }
    else this.korisnici=[]
  }*/
  //this.clanci = data;
  //console.log(this.clanci);
}
async saljiClanak(){
	const data = await this.historijaManagement.posaljiClanak(this.idVerzije);
	console.log(data);
}

  showAuthors(): string {
    return this.autori.join(", ");
  }

  onClickPrihvati(): void {
	  this.saljiClanak();
    alert("Pritisnut je button Prihvati");
  }

  onClickOdbij(): void {
    alert("Pritisnut je button Odbij");
  }
  openUser(id:number){
    console.log(id);
    this.router.navigate(['/userr/' + id.toString()]);
  }
}
