package cn.mayu.yugioh.library.application.include;

import cn.mayu.yugioh.library.application.include.command.IncludeCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.include.Include;
import cn.mayu.yugioh.library.domain.aggregate.include.IncludeRepository;
import cn.mayu.yugioh.library.domain.service.IncludeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncludeCommandService {

    @Autowired
    private IncludeService includeService;

    @Autowired
    private IncludeRepository includeRepository;

    public void createInclude(IncludeCreateCommand includeCreateCommand) {
        Include include = includeService.createInclude(
                includeCreateCommand.getPackageName(),
                includeCreateCommand.getSaleDate(),
                includeCreateCommand.getSerial(),
                includeCreateCommand.getRare(),
                includeCreateCommand.getPackShortName(),
                includeCreateCommand.getPassword());
        includeRepository.store(include);
        include.createInclude();
    }
}
