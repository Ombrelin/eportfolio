import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  constructor(
    private http: HttpClient
  ) {
  }

  async upload(file: File) {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return await this.http.post(`/files`, formData,
      {
        responseType: 'text'
      }
    ).toPromise();
  }

}
