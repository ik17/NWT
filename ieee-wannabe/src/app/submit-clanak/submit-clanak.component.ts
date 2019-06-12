import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from 'angularfire2/storage';
import { finalize } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { KorisnikManagementService } from '../_services/korisnik-management/korisnik-management.service';
import { ClanakService } from '../_services/clanak-management/clanak.service';
import { HistorijaService } from '../_services/historija-management/historija.service';
import { Clanak } from '../_services/historija-management/clanak';
import { Kategorija } from '../_services/clanak-management/kategorija';

import { Kategorija as HistoryKategorija} from '../_services/historija-management/kategorija';
import { Autori } from '../_services/historija-management/autori';
import { Korisnik } from '../_services/historija-management/korisnik';
import { Verzija } from '../_services/historija-management/verzija';


@Component({
  selector: 'app-submit-clanak',
  templateUrl: './submit-clanak.component.html',
  styleUrls: ['./submit-clanak.component.css']
})
export class SubmitClanakComponent implements OnInit {
	dropdownList = [];
  selectedItems = [];
  dropdownSettings = {};
  url:any;
  url2:string;
  naziv:string;
  ispis:string;
  clanak: Clanak;
  listaAutora:Korisnik[];
  constructor(private afStorage: AngularFireStorage,
    private korisnikManagementService: KorisnikManagementService, 
    private historijaService:HistorijaService,
    private clanakService: ClanakService) { }
  uploadProgress: Observable<number>;
  downloadURL: Observable<string>;
  ngOnInit() {
    this.clanak = new Clanak();
    this.getAuthors();
    this.getHistoryAuthors();
	  /*this.dropdownList = [
      { item_id: 1, item_text: 'Mumbai' },
      { item_id: 2, item_text: 'Bangaluru' },
      { item_id: 3, item_text: 'Pune' },
      { item_id: 4, item_text: 'Navsari' },
      { item_id: 5, item_text: 'New Delhi' }
    ];*/
    this.selectedItems = [];/*
    this.selectedItems = [
      { item_id: 3, item_text: 'Pune' },
      { item_id: 4, item_text: 'Navsari' }
    ];*/
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'username',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }
  async getAuthors(){
    const data = await this.korisnikManagementService.AllUsers();
    this.dropdownList = data;
    console.log(data);
  }
  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  handleFileInput(event) {
    const file = event.target.files[0];
    const filePath = 'name-your-file-path-here';
    const fileRef = this.afStorage.ref(filePath);
    const task = this.afStorage.upload(filePath, file);

    // observe percentage changes
    this.uploadProgress = task.percentageChanges();
    // get notified when the download URL is available
    task.snapshotChanges().pipe(
        finalize(() => this.downloadURL = fileRef.getDownloadURL() )
     )
    .subscribe(response => {
      this.url = response; 
    })
  }
  async getHistoryAuthors(){
    console.log("Uslo");
    const data = await this.historijaService.AllUsers();
    this.listaAutora = data;
    console.log(data);
  }
  async submitClanak(clanak:Clanak){
    const data = await this.historijaService.createClanak(clanak);
    console.log(data);
    //this.clanak = data;
    return data;
  }
  async getKategorija(){
    return await this.clanakService.oneKategorija(1);
  }
  async createAuthorHistory(author:Autori){
    console.log(author);
    const data = await this.historijaService.createAutor(author);
    console.log(data);
  }
  async dodajVerziju(a:Verzija){
    console.log(a);
    const data = await this.historijaService.createVerzija(a);
    console.log(data);
    return data;
  }
  async posaljiClanak(){
    if(this.url){
      this.ispis = "";
      console.log(this.url);
      this.url2 = this.url.task.uploadUrl_;
      console.log(this.url2);
      const data = await this.getKategorija();
      let k:Kategorija = data;
      let hk:HistoryKategorija = new HistoryKategorija(k.id, k.naziv);
      this.clanak.idKategorije = hk;
      this.clanak.naziv = this.naziv;
      this.clanak.odobrioClanak = null;
      this.clanak.clanakOdobren = 0;
      const id = await this.submitClanak(this.clanak);
      var s:String = this.url2; 
      console.log(typeof this.url2);
      console.log(s);
      const verzija = await this.dodajVerziju(new Verzija(id, 1, s, "something"));
      console.log(verzija);
      console.log(id);
      this.selectedItems.forEach(element => {
        console.log(this.listaAutora);
        this.listaAutora.forEach(element2 => {
            if(element2.id === element.id){
              this.createAuthorHistory(new Autori(element2, id));
              console.log("AddedAuthor");
              console.log(element2.username);
            }
        });
      });
    }
    else{
      this.ispis = "Članak nije unesen!";
    }
  }

}
