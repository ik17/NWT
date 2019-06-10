import { Component, OnInit } from '@angular/core';
import { AngularFireUploadTask, AngularFireStorageReference, AngularFireStorage } from 'angularfire2/storage';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {
  ref: AngularFireStorageReference;
  task: AngularFireUploadTask;
  nazivKorisnika: string = "Hanna Bojadžić";
  username: string ="wetania";
  biografija: string="Hanna Bojadžić je studentica četvrte godine elektrotehničkog fakulteta u Sarajevu i umjesto da živi svoj život ona piše sama svoju biografiju koja mora biti duža od 20 karaktera";
  datum: string="01.01.2019";
  fileToUpload: File = null;
  uploadProgress: Observable<number>;
  downloadURL: Observable<string>;
  clanci: any[] = [ {"nazivClanka":"Clanak 1", "autori":"Autor1", "kategorija":"Kategorija 1"}, 
  {"nazivClanka":"Clanak 2", "autori":"Autor2", "kategorija":"Kategorija 7"},
  {"nazivClanka":"Clanak 3", "autori":"Autor 3", "kategorija":"Kategorija 3"},
  {"nazivClanka":"Clanak 4", "autori":"Autor 17", "kategorija":"Kategorija 9"}, 
  {"nazivClanka":"Clanak 5", "autori":"Autor 5", "kategorija":"Kategorija 1"}] ;

 

  constructor(private afStorage: AngularFireStorage) { }

  ngOnInit() {
  }



  onClickPrihvati(): void {
    alert("Pritisnut je button Prihvati");
  }

  onClickOdbij(): void {
    alert("Pritisnut je button Odbij");
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
    .subscribe()
  }
    
    
    
    /*
    console.log("here");
    const id = Math.random().toString(36).substring(2);
    this.ref = this.afStorage.ref(id);
    this.task = this.ref.put(event.target.files[0]);
    this.uploadProgress = this.task.percentageChanges();
    this.downloadURL = this.ref.getDownloadURL();
    this.task.snapshotChanges().pipe(
      finalize(() => this.downloadURL = fileRef.getDownloadURL() )
   )
  .subscribe()
    console.log(this.downloadURL)
    console.log("Also here");
}*/

}
