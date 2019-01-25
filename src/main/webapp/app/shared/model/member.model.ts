import { IUser } from 'app/core/user/user.model';
import { IMemberOrder } from 'app/shared/model//member-order.model';

export interface IMember {
    id?: number;
    nickname?: string;
    user?: IUser;
    orders?: IMemberOrder[];
}

export class Member implements IMember {
    constructor(public id?: number, public nickname?: string, public user?: IUser, public orders?: IMemberOrder[]) {}
}
