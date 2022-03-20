import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Status } from '../enum/status.enum';
import { CustomResponse } from '../interface/custom-response';
import { Server } from '../interface/server';

@Injectable({ providedIn: 'root' })
export class ServerService
 {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // get All Servers
  servers$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/api/test/list`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  // save server
  save$ = (server: Server) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/api/test/save`, server)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  // Ping by ip address
  ping$ = (ipAddress: string) => <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/api/test/ping/${ipAddress}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  // filter by server status- take status and response and response with updated message and data status
  filter$ = (status: Status, response: CustomResponse) => <Observable<CustomResponse>>
    new Observable<CustomResponse>(
      suscriber => {
        console.log(response);
          suscriber.next(
            // if status = ALL set message inside response and return data as it is
            status === Status.ALL ? { ...response, message: `Servers filtered by ${status} status` } :
            {
              ...response,
                         message: 
                             // filter to check if any server is available
                            response.data.servers.filter(server => server.status === status).length > 0  ? 
                            // set corresponding message inside response and return it                          
                           `Servers filtered by ${status === Status.SERVER_UP ? 'SERVER UP': 'SERVER DOWN'} status` :
                            //  if server not found
                            `No servers of ${status} found`,
                         data:
                            { // return data by filter
                              servers: response.data.servers.filter(server => server.status === status)
                            }
            }   
         /*   response =>  
            {
              if(status == "ALL")
              {
                console.log("inside All Up"); 

                 return {...response, message: `Servers filtered by ${status} status`};
              }
              else if (response.data.servers.filter(server => server.status === status).length> 0) 
              {
                 if (status === Status.SERVER_UP)
                 {  
                  console.log("inside Server Up"); 
                     return {...response, message: `Servers filtered by server up`,
                     data: {servers: response.data.servers.filter(server => server.status === status)}}
                 }
                 else 
                 {
                 return {...response, message: `Servers filtered by server down`,
                 data: {servers: response.data.servers.filter(server => server.status === status)}}
                 }
              }
              else 
              {
                console.log("inside last "); 
                return {...response, message: `status: No server found`,
                data: {servers: response.data.servers.filter(server => server.status === status)}}
              }
            }     */
          
         );
        suscriber.complete();
      }
    )
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

   // delete by serverId
  delete$ = (serverId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/api/test/delete/${serverId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );


  // Error Handler
  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`An error occurred - Error code: ${error.status}`);
  }
}
