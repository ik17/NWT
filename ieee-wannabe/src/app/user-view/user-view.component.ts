import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {
  nazivKorisnika: string = "Hanna Bojadžić";
  username: string ="wetania";
  biografija: string="Hanna Bojadžić je studentica četvrte godine elektrotehničkog fakulteta u Sarajevu i umjesto da živi svoj život ona piše sama svoju biografiju koja mora biti duža od 20 karaktera";
  datum: string="01.01.2019";
  fileToUpload: File = null;

  clanci: any[] = [ {"nazivClanka":"Clanak 1", "autori":"Autor1", "kategorija":"Kategorija 1"}, 
  {"nazivClanka":"Clanak 2", "autori":"Autor2", "kategorija":"Kategorija 7"},
  {"nazivClanka":"Clanak 3", "autori":"Autor 3", "kategorija":"Kategorija 3"},
  {"nazivClanka":"Clanak 4", "autori":"Autor 17", "kategorija":"Kategorija 9"}, 
  {"nazivClanka":"Clanak 5", "autori":"Autor 5", "kategorija":"Kategorija 1"}] ;

 

  constructor() { }

  ngOnInit() {
  }



  onClickPrihvati(): void {
    alert("Pritisnut je button Prihvati");
  }

  onClickOdbij(): void {
    alert("Pritisnut je button Odbij");
  }
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
}

}
