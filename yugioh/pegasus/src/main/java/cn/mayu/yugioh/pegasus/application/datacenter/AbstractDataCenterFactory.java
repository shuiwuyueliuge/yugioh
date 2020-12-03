package cn.mayu.yugioh.pegasus.application.datacenter;

public abstract class AbstractDataCenterFactory implements DataCenterFactory {

    private String domain;

    private String description;

    private boolean exists;

    protected void setProperty(DataCenterConfiguration.DataCenterProperty property) {
        this.description = property.getDescription();
        this.domain = property.getDomain();
        this.exists = property.isExists();
    }

    @Override
    public boolean exists() {
        return this.exists;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public String domain() {
        return this.domain;
    }
}
