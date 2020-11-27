package cn.mayu.yugioh.pegasus.port.adapter.datacenter;

import java.util.Optional;

public abstract class AbstractDataCenter implements DataCenter {

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
    public Optional<String> domain() {
        return Optional.of(this.domain);
    }
}
