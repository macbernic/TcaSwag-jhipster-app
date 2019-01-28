import { IProduct } from 'app/shared/model/product.model';
import { IProductSku } from 'app/shared/model/product-sku.model';
import { IMemberOrder } from 'app/shared/model/member-order.model';

export interface IMemberOrderItem {
    id?: number;
    customText?: string;
    appliedPrice?: number;
    product?: IProduct;
    productSku?: IProductSku;
    memberOrder?: IMemberOrder;
}

export class MemberOrderItem implements IMemberOrderItem {
    constructor(
        public id?: number,
        public customText?: string,
        public appliedPrice?: number,
        public product?: IProduct,
        public productSku?: IProductSku,
        public memberOrder?: IMemberOrder
    ) {}
}
