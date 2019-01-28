import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMemberOrder } from 'app/shared/model/member-order.model';

type EntityResponseType = HttpResponse<IMemberOrder>;
type EntityArrayResponseType = HttpResponse<IMemberOrder[]>;

@Injectable({ providedIn: 'root' })
export class MemberOrderService {
    public resourceUrl = SERVER_API_URL + 'api/member-orders';

    constructor(protected http: HttpClient) {}

    create(memberOrder: IMemberOrder): Observable<EntityResponseType> {
        return this.http.post<IMemberOrder>(this.resourceUrl, memberOrder, { observe: 'response' });
    }

    update(memberOrder: IMemberOrder): Observable<EntityResponseType> {
        return this.http.put<IMemberOrder>(this.resourceUrl, memberOrder, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMemberOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMemberOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
