import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITcaMember } from 'app/shared/model/tca-member.model';

type EntityResponseType = HttpResponse<ITcaMember>;
type EntityArrayResponseType = HttpResponse<ITcaMember[]>;

@Injectable({ providedIn: 'root' })
export class TcaMemberService {
    public resourceUrl = SERVER_API_URL + 'api/tca-members';

    constructor(protected http: HttpClient) {}

    create(tcaMember: ITcaMember): Observable<EntityResponseType> {
        return this.http.post<ITcaMember>(this.resourceUrl, tcaMember, { observe: 'response' });
    }

    update(tcaMember: ITcaMember): Observable<EntityResponseType> {
        return this.http.put<ITcaMember>(this.resourceUrl, tcaMember, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITcaMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITcaMember[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
