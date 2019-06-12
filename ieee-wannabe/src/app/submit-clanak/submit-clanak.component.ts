import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from 'angularfire2/storage';
import { finalize } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { KorisnikManagementService } from '../_services/korisnik-management/korisnik-management.service';
import { ClanakService } from '../_services/clanak-management/clanak.service';
import { HistorijaService } from '../_services/historija-management/historija.service';
import { Clanak } from '../_services/historija-management/clanak';

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
  constructor(private afStorage: AngularFireStorage,
    private korisnikManagementService: KorisnikManagementService, 
    private historijaService:HistorijaService) { }
  uploadProgress: Observable<number>;
  downloadURL: Observable<string>;
  ngOnInit() {
    this.getAuthors();
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
  async submitClanak(clanak:Clanak){
    const data = await this.historijaService.createClanak(clanak);
    console.log(data);
    this.clanak = data;
  }
  async posaljiClanak(){
    if(this.url){
      this.ispis = "";
      console.log(this.url);
      this.url2 = this.url.task.uploadUrl_;
      console.log(this.url2);
      await this.submitClanak(this.clanak);
    }
    else{
      this.ispis = "Članak nije unesen!";
    }
  }

}
