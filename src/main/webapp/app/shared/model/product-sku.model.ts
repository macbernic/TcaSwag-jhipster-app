import { ITcaOrder } from 'app/shared/model//tca-order.model';
import { IProduct } from 'app/shared/model//product.model';

export const enum ProductSize {
    UNIQUE = 'UNIQUE',
    XSMALL = 'XSMALL',
    SMALL = 'SMALL',
    MEDIUM = 'MEDIUM',
    LARGE = 'LARGE',
    XLARGE = 'XLARGE',
    XXLARGE = 'XXLARGE'
}

export const enum GenderType {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    BOTH = 'BOTH'
}

export interface IProductSku {
    id?: number;
    size?: ProductSize;
    gender?: GenderType;
    customText?: string;
    tcaOrder?: ITcaOrder;
    product?: IProduct;
}

export class ProductSku implements IProductSku {
    constructor(
        public id?: number,
        public size?: ProductSize,
        public gender?: GenderType,
        public customText?: string,
        public tcaOrder?: ITcaOrder,
        public product?: IProduct
    ) {}
}
