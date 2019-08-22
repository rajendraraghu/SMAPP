import { Moment } from 'moment';

export interface ISnowParse {
  id?: number;
  name?: string;
  description?: string;
  sourceType?: string;
  sourcePath?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  sourceConnectionName?: string;
  sourceConnectionId?: number;
  snowflakeConnectionName?: string;
  snowflakeConnectionId?: number;
  runBy?: string;
}

export class SnowParse implements ISnowParse {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public sourceType?: string,
    public sourcePath?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public sourceConnectionName?: string,
    public sourceConnectionId?: number,
    public snowflakeConnectionName?: string,
    public snowflakeConnectionId?: number,
    public runBy?: string
  ) {}
}
